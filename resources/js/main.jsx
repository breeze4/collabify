import React from 'react';
import Playlist from './playlist.jsx';

class CollabifyApplication extends React.Component {
    render() {
        return (<Playlist message='hi from app'/>);
    }
}

export default CollabifyApplication;

React.render(
    <CollabifyApplication />,
    document.getElementById('container')
);
