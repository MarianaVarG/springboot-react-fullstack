import "bootstrap/dist/css/bootstrap.min.css";

import Navigation from "./layouts/Navigation";

import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Container } from "react-bootstrap";
import { Provider } from "react-redux";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.min.css"
import 'react-confirm-alert/src/react-confirm-alert.css';

import SignIn from "./pages/SignIn";
import Home from "./pages/Posts";
import NewPost from "./pages/NewPost";
import store from "./store";
import checkForToken from "./helpers/checkForToken";
import PrivateRoute from "./utils/PrivateRoute";
import UserPosts from "./pages/UserPosts";
import SignUp from "./pages/SingUp";
import PostDetails from "./pages/PostDetails";
import EditPost from "./pages/EditPost";

/**
 * Moment config
 */
import moment from "moment";
import 'moment/locale/es'
moment.locale('es')

checkForToken();

function App() {
  return (
    <Provider store={store}>
      <Router>
        <Navigation />

        <Container>
          <ToastContainer /> {/* Glogal toasty for alert */}
          <Routes>
            <Route exact path="/" element={<Home />} />
            <Route exact path="/signin" element={<SignIn />} />
            <Route exact path="/signup" element={<SignUp />} />
            <Route exact path="/post/:id" element={<PostDetails />} />
            <Route exact path="/posts" element={<PrivateRoute > <UserPosts /> </PrivateRoute>} />
            <Route exact path="/newpost" element={<PrivateRoute > <NewPost /> </PrivateRoute>} />
            <Route exact path="/editpost/:id" element={<PrivateRoute > <EditPost /> </PrivateRoute>} />
          </Routes>
        </Container>
      </Router>
    </Provider>
  );
}

export default App;
