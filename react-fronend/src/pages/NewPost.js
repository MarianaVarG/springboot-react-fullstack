import React, { useState } from 'react'
import validator from 'validator';

import { Container, Row, Col, Card, Alert } from 'react-bootstrap';
import { isObjectEmpty } from '../helpers/helpers';
import { useNavigate } from 'react-router-dom';

import NewPostForm from '../components/forms/NewPostForm';
import { exposures } from '../helpers/exposured';
import axios from 'axios';
import { CREATE_POST_ENDPOINT } from '../helpers/endpoints';
import { toast } from 'react-toastify';
import { useDispatch } from 'react-redux';
import { getUserPosts } from '../actions/postActions';

export default function NewPost() {
    const [errors, setErrors] = useState({}) // Empty object
    // Instead of useHistory
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const newPost = async ({ title, content, expirationTime, exposureId }) => {
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
            const response = await axios.post(CREATE_POST_ENDPOINT, { title, content, expirationTime, exposureId });
            await dispatch(getUserPosts());
            toast.info('Post created!', {
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
                newpost: error.response.data.message
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
                            {errors.newpost && <Alert variant='danger'>{errors.newpost}</Alert>}

                            <h3 className='fw-bold mb-2 text-uppercase'>New Post</h3>
                            <hr></hr>

                            <NewPostForm errors={errors} onSubmitCallback={newPost}></NewPostForm>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    )
}
