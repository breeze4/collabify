import React from 'react';
import Router from 'react-router';
import { DefaultRoute, Link, Route, RouteHandler } from 'react-router';

import PlaylistsHandler from './components/playlists.jsx';

var wrapComponent = function (Component, props) {
    return React.createClass({
        render: function () {
            return React.createElement(Component, props);
        }
    });
};

let App = React.createClass({
    getIntialState() {
        var state = {};
        state.userId = window.localStorage.getItem('collabify.userId');
        state.accessToken = window.localStorage.getItem('collabify.accessToken');

        return state;
    },
    render() {
        return (
            <div className="nav">
                <Link to="app">Home</Link>
                <Link to="playlists">Playlists</Link>

                {}
                <RouteHandler/>
            </div>
        );
    }
});

let routes = (
    <Route name="app" path="/" handler={App}>
        <Route name="playlists" path="/playlists" handler={wrapComponent(PlaylistsHandler, {currentUser: null})}/>
    </Route>
);

// add Router.HistoryLocation to remove hash
Router.run(routes,  function (Handler) {
    React.render(<Handler/>, document.body);
});