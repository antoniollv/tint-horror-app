// src/components/SlideShow.js
import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import { ReactSVG } from 'react-svg';

const ImageContainer = styled.div`
  width: 100%;
  max-width: 2040px;
  height: auto;
  display: block;
  margin: 0 auto;
`;

const Image = styled(ReactSVG)`
  width: 100%;
  height: auto;
`;

const SlideShow = ({ images }) => {
  const [currentIndex, setCurrentIndex] = useState(0);

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % images.length);
    }, 3000); // Cambia cada 3 segundos

    return () => clearInterval(interval);
  }, [images.length]);

  return (
    <ImageContainer>
      <Image src='assets/tinwebp.svg' />
    </ImageContainer>
  );
};

export default SlideShow;
