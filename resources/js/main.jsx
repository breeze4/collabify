import React from 'react';
import Login from './login.jsx';

class CollabifyApplication extends React.Component {
    render() {
        return (<Login />);
    }
}

export default CollabifyApplication;

React.render(
    <CollabifyApplication />,
    document.getElementById('container')
);
