import React from "react";

class Playlist extends React.Component {
    static willTransitionTo(transition, params, query) {
        console.log(params, query);
    }
    render() {
        return (<p>{this.props.message}</p>);
    }
}

export default Playlist;