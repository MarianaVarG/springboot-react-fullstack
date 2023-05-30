import { SET_USER_POST } from "../actions/types"

// State for login and register new users
const initialState = { posts: [], fetched: false };

export default function authStates(state = initialState, action) {
    // Const to get type action and data to send 
    const { type, payload } = action;

    switch (type) {
        case SET_USER_POST:
            return {
                ...state,
                fetched: payload.fetched,
                posts: payload.posts
            }
        default:
            return state;
    }
}