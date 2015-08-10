import React from 'react';
import Login from './login.jsx';
import Playlist from './playlist.jsx';
import Dashboard from './dashboard.jsx';
import Router from 'react-router';

class CollabifyApplication extends React.Component {
    static willTransitionTo(transition, params, query) {
        console.log(params, query);
    }

    doSomething() {
        var router = this.context.router;
        var params = router.getCurrentParams();
        var propParams = this.props.query;
        console.log(params, propParams);
    }

    render() {
        return (
            <div>
                <header>
                    <h1>Collabify</h1>
                    <ul>
                        <li>
                            <Link to="app">Dashboard</Link>
                        </li>
                        <li>
                            <Link to="playlists">Playlists</Link>
                        </li>
                    </ul>
                Logged in {this.doSomething()} {this.props.query && this.props.query.user_id}
                </header>
                <RouteHandler/>
                <Login />
            </div>);
    }
}
CollabifyApplication.contextTypes = {
    router: React.PropTypes.func
}

export default CollabifyApplication;

var DefaultRoute = Router.DefaultRoute;
var Link = Router.Link;
var Route = Router.Route;
var RouteHandler = Router.RouteHandler;
var routes = (
    <Route name="app" path="/" handler={CollabifyApplication}>
        <Route name="playlists" handler={Playlist}/>
        <DefaultRoute handler={Dashboard}/>
    </Route>
);

Router.run(routes, function (Handler) {
    React.render(<Handler/>,
        document.getElementById('container'));
});
