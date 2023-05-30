import axios from "axios";
import { SET_USER_POST } from "./types";
import { USER_POSTS_ENDPOINT } from "../helpers/endpoints";

export const getUserPosts = () => dispatch => {
    return new Promise((resolve, reject) => {
        axios.get(USER_POSTS_ENDPOINT)
            .then(response => {
                dispatch({
                    type: SET_USER_POST,
                    payload: { fetched: true, posts: response.data }
                })
                resolve(response)
            })
            .catch(error => {
                reject(error)
            })
    })
}
