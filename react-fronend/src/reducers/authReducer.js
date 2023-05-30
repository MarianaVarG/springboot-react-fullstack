import { SET_CURRENT_USER } from "../actions/types"

// State for login and register new users
const initialState = { loggedIn: false, user: {} };

export default function authStates(state = initialState, action) {
    // Const to get type action and data to send 
    const { type, payload } = action;

    if (type === SET_CURRENT_USER) {
        return {
            ...state,
            loggedIn: payload.loggedIn,
            user: payload.user
        }
    } else {
        return state;
    }
}