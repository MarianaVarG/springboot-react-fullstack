import 'bootstrap/dist/css/bootstrap.min.css';

import React from 'react';
import { Badge, Button, Card } from 'react-bootstrap';
import { Link, NavLink } from 'react-router-dom';
import moment from 'moment/moment';
import { exposures } from '../../helpers/exposured';
import { FiEdit } from 'react-icons/fi';
import DeletePostsButton from './buttons/DeletePostsButton';

export default function PrivatePost({ post }) {
    return (
        <Card className='mb-4'>
            {/* to pass a parameter use ` */}
            <Card.Header as="h5"> <Link to={`/post/${post.postId}`}> {post.title} </Link> </Card.Header>
            <Card.Body>
                <Card.Text>
                    Created by: {post.user.firstName}, {moment(post.createdAt).fromNow()}
                </Card.Text>
            </Card.Body>
            <Card.Footer className='d-flex justify-content-between'>
                <div>
                    <Badge className='me-2 bg-secondary'> {post.exposure.type} </Badge>
                    {post.expired && post.exposure.id === exposures.PUBLIC && <Badge className='me-2 bg-danger'> Expired </Badge>}
                </div>
                <div className="d-flex">
                    <span className="d-flex align-items-center">
                        <Button
                            variant='warning'
                            size='sm'
                            className='me-2 d-flex align-items-center'
                            as={NavLink}
                            to={`/editpost/${post.postId}`}>
                            <FiEdit className='me-1' /> Edit
                        </Button>
                    </span>
                    <span className="d-flex align-items-center">
                        <DeletePostsButton postId={post.postId} title={post.title} />
                    </span>
                </div>

            </Card.Footer>
        </Card>
    )
}
