import axios from 'axios';
import React, { useState, useEffect } from 'react';

import { PUBLIC_POSTS_ENDPOINT  } from '../helpers/endpoints';

export default function Posts() {
  const [posts, setPosts] = useState([]); // Posts array
  const [fetching, setFeching] = useState(true); // Whene get data from our API - false means we finish

  useState(() => {
    // Get petition
    axios.get(PUBLIC_POSTS_ENDPOINT).then(response => {
      setPosts(response.data);
      setFeching(false);
    }).catch(err => {
      console.error(err);
      setFeching(false);
    })
  })

  return (
    <div>Posts</div>
  )
}
