import axios from 'axios';
import React from 'react';
import { Button } from 'react-bootstrap';
import { confirmAlert } from 'react-confirm-alert';
import { RiDeleteBin6Line } from 'react-icons/ri';
import { useDispatch } from 'react-redux';
import { getUserPosts } from '../../../actions/postActions';
import { DELETE_POST_ENDPOINT } from '../../../helpers/endpoints';
import { toast } from 'react-toastify';

export default function DeletePostsButton({ postId, title }) {
    const dispatch = useDispatch();

    const createAlert = () => {
        confirmAlert({
            title: "Delete post",
            message: `Are you sure delete post ${title}? `,
            buttons: [
                {
                    label: "Delete",
                    onClick: () => { deletePost() }
                },
                {
                    label: "No",
                    onClick: () => { return false; }
                }
            ]
        })
    }

    const deletePost = async () => {
        try {
            await axios.delete(`${DELETE_POST_ENDPOINT}/${postId}`);
            await dispatch(getUserPosts());
            toast.info('Post deleted!', {
                position: "bottom-center",
                autoClose: 2000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
        } catch (error) {
            toast.error(error.response.data.message, {
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

    return (
        <Button 
        variant='danger' 
        size='sm' 
        className="d-flex align-items-center"
        onClick={ createAlert }>
            <RiDeleteBin6Line className='me-1' /> Delete
        </Button>
    )
}
