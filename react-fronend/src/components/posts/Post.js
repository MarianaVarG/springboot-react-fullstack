import React from 'react';
import { Card } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import moment from 'moment/moment';

export default function Post({ post }) {
  return (
    <Card className='mb-4'>
        <Card.Header as="h5"> <Link to={'/'}> { post.title } </Link> </Card.Header>
        <Card.Body>
            <Card.Text>
                { post.content }
            </Card.Text>
            <Card.Text>
                Created by: { post.user.firstName }, { moment(post.createdAt).fromNow() }
            </Card.Text>
        </Card.Body>
    </Card>
    
  )
}
