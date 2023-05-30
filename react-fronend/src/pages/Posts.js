import axios from 'axios';
import React, { useState, useEffect } from 'react';

import { PUBLIC_POSTS_ENDPOINT } from '../helpers/endpoints';
import PublicPost from '../components/posts/PublicPost';
import Placeholder from '../components/utils/Placeholder';
import NoPosts from '../components/utils/NoPosts';

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
      
      { fetching && <Placeholder /> }
      { !fetching && posts.length === 0 && <NoPosts text={'No public posts avalilables'}/>}
  
      <div>
        {posts.map(post => <PublicPost key={post.postId} post={post}></PublicPost>)}
      </div>
    </div>
  )
}
