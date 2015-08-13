import React from 'react';
import $ from 'jquery';

let Playlists = React.createClass({
    getInitialState: function() {
        return {
            items: []
        };
    },

    componentDidMount() {
        var self = this;

        $.ajax('/playlist/abc', {
            success: function (response) {
                self.setState({
                    items: response && response.items || []
                });
            }
        });
    },

    render() {
        var items = this.state.items;
        return (
            <div>Welcome to playlists or not
                <ul>
                    {items.map(function(item) {
                        return <li key={item.name}>{item.name}</li>
                    })}
                </ul>
            </div>);
    }
});

export default Playlists;