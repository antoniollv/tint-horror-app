// src/components/BackgroundImage.js
import React from 'react';
import styled from 'styled-components';
import SvgLoader from './SvgLoader.jsx';

const ImageContainer = styled.div`
  z-index: 0;
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  background: transparent;
  overflow: hidden;
`;


const BackgroundImage = ({ src, onLoad }) => (
  <ImageContainer>
    <SvgLoader src={src} onLoad={onLoad} />
  </ImageContainer>
);

export default BackgroundImage;