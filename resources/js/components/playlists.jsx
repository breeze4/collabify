import React from 'react';
import $ from 'jquery';

let Playlists = React.createClass({

    componentDidMount() {
        $.get('')
    },

    render() {
        return (
            <div>Welcome to playlists or not
            </div>);
    }
});

export default Playlists;