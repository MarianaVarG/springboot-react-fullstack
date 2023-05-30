import { combineReducers } from "@reduxjs/toolkit";
import authReducer from "./authReducer";
import userPostReducer from "./userPostReducer"

export default combineReducers ({
    auth: authReducer,
    posts: userPostReducer
})