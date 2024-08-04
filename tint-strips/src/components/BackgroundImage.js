// src/components/BackgroundImage.js
import React from 'react';
import styled from 'styled-components';
import SvgLoader from './SvgLoader';

const ImageContainer = styled.div`  
  z-index: 0;
  width: 100%; /* O el tamaño deseado del contenedor */
  height: auto; /* Ajusta la altura automáticamente si es necesario */
  display: block; /* O usa block si no necesitas centrar la imagen */
  justify-content: center; /* Centra la imagen horizontalmente */
  align-items: center; /* Centra la imagen verticalmente */
  overflow: hidden; /* Oculta cualquier parte de la imagen que se desborde */
`;


const BackgroundImage = ({ src }) => (
  <ImageContainer>
    <SvgLoader src={src} />
  </ImageContainer>
);

export default BackgroundImage;
