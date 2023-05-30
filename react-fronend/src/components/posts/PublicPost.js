import React from 'react';
import { Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import moment from 'moment/moment';

export default function PublicPost({ post }) {
  return (
    <Card className='mb-4'>
      {/* to pass a parameter use ` */}
      <Card.Header as="h5"> <Link to={`/post/${post.postId}`}> {post.title} </Link> </Card.Header>
      <Card.Body>
        <Card.Text>
          Created by: {post.user.firstName}, {moment(post.createdAt).fromNow()}
        </Card.Text>
      </Card.Body>
    </Card>
  )
}
