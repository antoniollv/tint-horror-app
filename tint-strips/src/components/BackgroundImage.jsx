// src/components/BackgroundImage.js
import React from 'react';
import styled from 'styled-components';
import SvgLoader from './SvgLoader.jsx';

const ImageContainer = styled.div`  
  z-index: 0;
  width: 100%; /* O el tamaño deseado del contenedor */
  height: auto; /* Ajusta la altura automáticamente si es necesario */
  display: block; /* O usa block si no necesitas centrar la imagen */
  justify-content: center; /* Centra la imagen horizontalmente */
  align-items: center; /* Centra la imagen verticalmente */
  overflow: hidden; /* Oculta cualquier parte de la imagen que se desborde */

  /* Media query para pantallas más pequeñas */
  @media (max-width: 768px) {
    height: 90vh; /* Ajusta la altura a 80% del viewport en tablets */
    display: flex;
    svg {
      width: 300pt; /* Sobrescribir el ancho del SVG en móviles */
    }
  }

  @media (max-width: 480px) {
    height: 90vh; /* Ajusta la altura a 70% del viewport en teléfonos */
    padding: 10px; /* Añadir padding para evitar que se vea muy comprimido */
    display: flex;
    svg {
      width: 300pt; /* Sobrescribir el ancho del SVG en móviles */
    }
  }
`;


const BackgroundImage = ({ src, onLoad }) => (
  <ImageContainer>
    <SvgLoader src={src} onLoad={onLoad} />
  </ImageContainer>
);

export default BackgroundImage;