import React, { useState } from "react";
import { Form, Button, Row, Col } from "react-bootstrap";
import { exposures } from '../../helpers/exposured';

export default function NewPostForm({ errors, onSubmitCallback, postTitle = "", postContent = "", postExposureId = exposures.PUBLIC, postExpirationTime = 60, textButton = "Create new post" }) {
    // Setters
    const [title, setTitle] = useState(postTitle);
    const [content, setContent] = useState(postContent);
    const [expirationTime, setExpirationTime] = useState(postExpirationTime);
    const [exposureId, setExposureId] = useState(postExposureId);

    // Function used when submitting the form
    const submitForm = (e) => {
        e.preventDefault(); 
        // Send email and password to the father
        onSubmitCallback({ title, content, expirationTime, exposureId });
    }

    // Form with bootstrap
    return (
        // onSubmit call submitForrm function
        <Form onSubmit={submitForm}>
            <Form.Group controlId="title" className="mb-3">
                <Form.Label>Title</Form.Label>

                <Form.Control
                    type="text"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                    placeholder="ex. Snippet to print and array"
                    isInvalid={errors.title} // When errors.title is not empty, meand there are and error
                />
                {/* Displayed when an title validation error occurs */}
                <Form.Control.Feedback type="invalid">
                    {errors.title}
                </Form.Control.Feedback>
            </Form.Group>

            <Row>
                <Col md='6' xs='12'>
                    <Form.Group controlId='expirationTime'>
                        <Form.Label>Expiration time</Form.Label>

                        <Form.Control
                            disabled={ exposureId === exposures.PRIVATE }
                            as='select'
                            value={expirationTime}
                            onChange={(e) => setExpirationTime(e.target.value)}
                        >
                            <option value="30">30 minutes</option>
                            <option value="60">1 hour</option>
                            <option value="120">1 hours</option>
                            <option value="360">6 hours</option>
                            <option value="720">12 hours</option>
                            <option value="1440">1 day</option>
                        </Form.Control>
                        <Form.Control.Feedback type="invalid">
                            {errors.expirationTime}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Col>

                <Col md="6" xs="12">
                    <Form.Group controlId="exposureId">
                        <Form.Label>Exposure</Form.Label>
                        <div>
                            <Form.Check
                                onChange={() => setExposureId(exposures.PRIVATE)}
                                checked={ exposureId === exposures.PRIVATE }
                                inline
                                label="Private"
                                name="exposureId"
                                type="radio"
                                id="privateExposure"
                            />
                            <Form.Check
                                onChange={() => setExposureId(exposures.PUBLIC)}
                                checked={ exposureId === exposures.PUBLIC }
                                inline
                                label="Public"
                                name="exposureId"
                                type="radio"
                                id="publicExposure"
                            />
                        </div>


                        <Form.Control.Feedback type="invalid">
                            {errors.exposureId}
                        </Form.Control.Feedback>
                    </Form.Group>
                </Col>
            </Row>

            <Form.Group controlId="content" className="mb-3">
                <Form.Label>Content</Form.Label>

                <Form.Control
                    as="textarea"
                    rows={10}
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                    isInvalid={errors.content} // When errors.title is not empty, meand there are and error
                />
                {/* Displayed when an title validation error occurs */}
                <Form.Control.Feedback type="invalid">
                    {errors.content}
                </Form.Control.Feedback>
            </Form.Group>


            <div className="d-grid">
                <Button variant="outline-success" type="submit">
                    { textButton }
                </Button>
            </div>
        </Form>
    )

}