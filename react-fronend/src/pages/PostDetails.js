import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { POST_DETAILS_ENDPOINT } from '../helpers/endpoints';
import { Button, Card } from 'react-bootstrap';
import { Prism as SyntaxHighlighter } from 'react-syntax-highlighter';
import { oneLight } from 'react-syntax-highlighter/dist/esm/styles/prism';
import { CopyToClipboard } from 'react-copy-to-clipboard';
import axios from 'axios';
import moment from 'moment';
import { toast } from 'react-toastify';
import { downloadTextAsFile } from '../helpers/helpers';


export default function PostDetails() {
    // 'id' because I called 'id' on App.js
    const { id } = useParams();
    const [post, setPost] = useState(null);
    const navigate = useNavigate();

    /* Use effect is a hoock */
    useEffect(() => {
        // Get petition
        axios.get(`${POST_DETAILS_ENDPOINT}/${id}`).then(response => {
            setPost(response.data);
        }).catch(err => {
            // Private post or is expired
            navigate("/")
        })
    }, [id, navigate]);

    return (
        <div className='pb-4'>
            {/*All in a fragment inside an if because we initialize post as null. 
            If it detects that the post is already there, then it will render.*/}
            {post && (
                <React.Fragment>
                    <div className='jumbotron container-fluid bg-dark text-light p-2'>
                        <div className="container bg-dark p-5">
                            <h1> {post.title} </h1>
                            <p> Created by: {post.user.firstName}, {moment(post.createdAt).fromNow()} </p>
                        </div>
                    </div>

                    <hr className="my-4" />

                    <Card>
                        <Card.Header>
                            <Button variant='primary' size='sm' className='me-2'
                                onClick={() => {
                                    downloadTextAsFile(post.postId, post.content)
                                }}>Download
                            </Button>

                            <CopyToClipboard
                                onCopy={() => {
                                    toast.info('Copy to clipboard!', {
                                        position: "bottom-center",
                                        autoClose: 2000,
                                        hideProgressBar: false,
                                        closeOnClick: true,
                                        pauseOnHover: true,
                                        draggable: true,
                                        progress: undefined,
                                        theme: "light",
                                    });
                                }}
                                text={post.content}>
                                <Button variant='primary' size='sm'
                                    onClick={() => {

                                    }}>Copy to clipboard
                                </Button>
                            </CopyToClipboard>
                        </Card.Header>
                        <Card.Body>
                            <SyntaxHighlighter showLineNumbers wrapLines={true} style={oneLight}>
                                {post.content}
                            </SyntaxHighlighter>
                        </Card.Body>
                    </Card>
                </React.Fragment>
            )}
        </div>
    )
}
