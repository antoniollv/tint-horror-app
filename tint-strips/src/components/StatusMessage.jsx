import React from 'react';

const StatusMessage = ({ title, message }) => (
  <div className="status-message">
    <h2>{title}</h2>
    <p>{message}</p>
  </div>
);

export default StatusMessage;
