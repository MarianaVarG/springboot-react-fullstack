import React, { useEffect, useState } from 'react'
import validator from 'validator';

import { Container, Row, Col, Card, Alert } from 'react-bootstrap';
import { isObjectEmpty } from '../helpers/helpers';
import { useNavigate, useParams } from 'react-router-dom';

import NewPostForm from '../components/forms/NewPostForm';
import { exposures } from '../helpers/exposured';
import axios from 'axios';
import { POST_DETAILS_ENDPOINT, EDIT_POST_ENDPOINT } from '../helpers/endpoints';
import { toast } from 'react-toastify';
import { useDispatch } from 'react-redux';
import { getUserPosts } from '../actions/postActions';

export default function EditPost() {

    const { id } = useParams();
    const [errors, setErrors] = useState({}) // Empty object
    const [post, setPost] = useState(null);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    /* Use effect is a hoock */
    useEffect(() => {
        // Get petition
        axios.get(`${POST_DETAILS_ENDPOINT}/${id}`).then(response => {
            setPost(response.data);
        }).catch(err => {
            navigate("/")
        })
    }, [id, navigate]);


    const editPost = async ({ title, content, expirationTime, exposureId }) => {
        const errors = {};
        // Validate fields
        if (validator.isEmpty(title)) {
            errors.title = "Title is mandatory"
        }
        if (validator.isEmpty(content)) {
            errors.content = "Content is mandatory"
        }
        if (!isObjectEmpty(errors)) {
            setErrors(errors);
            return;
        }

        expirationTime = exposureId === exposures.PRIVATE ? 0 : expirationTime;

        try {
            const response = await axios.put(`${EDIT_POST_ENDPOINT}/${post.postId}`, { title, content, expirationTime, exposureId });
            await dispatch(getUserPosts());
            toast.info('Post updated!', {
                position: "bottom-center",
                autoClose: 2000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
            navigate(`/post/${response.data.postId}`)
        } catch (error) {
            setErrors({
                editPost: error.response.data.message
            })
        }
    }


    return (
        // Margin-Top = 5
        <Container className='mt-5 mb-5'>
            <Row>
                <Col sm='12' lg={{ span: 10, offset: 1 }}>
                    <Card className='shadow'>
                        <Card.Body>
                            {errors.editPost && <Alert variant='danger'>{errors.editPost}</Alert>}

                            <h3 className='fw-bold mb-2 text-uppercase'>Edit post</h3>
                            <hr></hr>

                            {post && <NewPostForm
                                errors={errors}
                                onSubmitCallback={editPost}
                                postTitle={post.title}
                                postContent={post.content}
                                postExposureId={post.exposure.id}
                                postExpirationTime={post.expirationTime}
                                textButton='Edit post '
                            />}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    )
}
