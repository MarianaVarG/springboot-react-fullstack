import React, { useState, useEffect } from 'react';

import PrivatePost from '../components/posts/PrivatePost';
import Placeholder from '../components/utils/Placeholder';
import { useDispatch, useSelector } from 'react-redux';
import { getUserPosts } from '../actions/postActions';
import { toast } from 'react-toastify';
import NoPosts from '../components/utils/NoPosts';

export default function UserPosts() {
  // const [posts, setPosts] = useState([]); // Posts array
  const [fetching, setFeching] = useState(true); // Whene get data from our API - false means we finish
  const fetched = useSelector(state => state.posts.fetched);
  const posts = useSelector(state => state.posts.posts);
  const dispatch = useDispatch();

  /* Use effect is a hoock */
  useEffect(() => {
    async function fetchedPosts() {
      if (!fetched) {
        try {
          setFeching(true);
          dispatch(getUserPosts());
          setFeching(false);
        } catch (err) {
          toast.error(err.response.data.message, {
            position: "bottom-center",
            autoClose: 2000,
            hideProgressBar: false,
            closeOnClick: true,
            pauseOnHover: true,
            draggable: true,
            progress: undefined,
            theme: "light",
          });
        }
      }
    }
    fetchedPosts();
  }, [dispatch, fetched]);

  return (
    <div>
      <div className='jumbotron container-fluid bg-dark text-light p-2'>
        <div className="container bg-dark p-5">
          <h1>My Posts</h1>
        </div>
      </div>

      <hr className="my-4" />

      { !fetched && <Placeholder /> }
      { !fetching && posts.length === 0 && <NoPosts  text={'No posts avalilables'}/>}

      <div>
        {posts.map(post => <PrivatePost key={post.postId} post={post}></PrivatePost>)}
      </div>
    </div>
  )
}
