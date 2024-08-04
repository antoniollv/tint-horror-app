// src/components/Thumbnail.js
import React from 'react';
import '../styles/Thumbnail.css'; 

function Thumbnail({ src, onClick, isActive }) {
  return (
    <div className={`thumbnail ${isActive ? 'active' : ''}`} onClick={onClick}>
      <img src={src} alt="thumbnail" />
    </div>
  );
}

export default Thumbnail;
