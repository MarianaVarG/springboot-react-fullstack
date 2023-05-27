import axios from 'axios';
import React, { useState, useEffect } from 'react';

import { PUBLIC_POSTS_ENDPOINT } from '../helpers/endpoints';
import Post from '../components/posts/Post';

export default function Posts() {
  const [posts, setPosts] = useState([]); // Posts array
  const [fetching, setFeching] = useState(true); // Whene get data from our API - false means we finish

  /* Use effect is a hoock */
  useEffect(() => {
    // Get petition
    axios.get(PUBLIC_POSTS_ENDPOINT).then(response => {
      setPosts(response.data);
      setFeching(false);
    }).catch(err => {
      console.error(err);
      setFeching(false);
    })
  }, [/* Only one call */]);

  return (
    <div>
      <div className='jumbotron container-fluid bg-dark text-light p-2'>
        <div className="container bg-dark p-5">
          <h1>Last public posts</h1>
        </div>
      </div>

      <hr className="my-4" />

      <div>
        {posts.map(post => <Post key={post.postId} post={post}></Post>)}
      </div>
    </div>
  )
}
