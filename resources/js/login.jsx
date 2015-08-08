import React from "react";

class Login extends React.Component {
    render() {
        return (<div>
            <div id="login">
                <h1>First, log in to spotify</h1>
                <a href="/login">Log in</a>
            </div>
            <div id="loggedin">
            </div>
        </div>);
    }
}

class LoggedIn extends React.Component {
    render() {
        return (<div>
            <h1>Logged in as {this.props.display_name}</h1>
            <img id="avatar" width="200" src="{this.props.images[0].url}" />
            <dl>
                <dt>Display name</dt>
                <dd>{this.props.display_name}</dd>
                <dt>Username</dt>
                <dd>{this.props.id}</dd>
                <dt>Email</dt>
                <dd>{this.props.email}</dd>
                <dt>Spotify URI</dt>
                <dd>
                    <a href="{this.props.external_urls.spotify}">{this.props.external_urls.spotify}</a>
                </dd>
                <dt>Link</dt>
                <dd>
                    <a href="{this.props.href}">{this.props.href}</a>
                </dd>
                <dt>Profile Image</dt>
                <dd>{this.props.images[0].url}</dd>
            </dl>
            <p>
                <a href="/">Log in again</a>
            </p>
        </div>);
    }
}

export default Login;