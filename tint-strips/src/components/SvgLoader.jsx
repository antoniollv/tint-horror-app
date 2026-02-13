import React, { useEffect, useState } from 'react';
import { fetchText } from '../utils/http.js';
import { logError } from '../utils/logger.js';

const SvgLoader = ({ src, onLoad }) => {
  const [svgContent, setSvgContent] = useState('');

  useEffect(() => {
    const fetchSvg = async () => {
      try {
        const text = await fetchText(src);
        const cleanedSvg = cleanSvg(text);
        setSvgContent(cleanedSvg);
      } catch (error) {
        logError('Error loading SVG', error);
      }
    };

    fetchSvg();
  }, [src]);

  const cleanSvg = (content) => {
    const parser = new DOMParser();
    const xmlDoc = parser.parseFromString(content, 'image/svg+xml');
    
    const allowedTags = ['svg', 'path', 'circle', 'rect', 'line', 'polyline', 'polygon', 'text', 'g', 'image'];
    const elements = xmlDoc.getElementsByTagName('*');

    for (let i = elements.length - 1; i >= 0; i--) {
      const node = elements[i];
      if (!allowedTags.includes(node.tagName)) {
        node.parentNode.removeChild(node);
      }
    }

    const svgElement = xmlDoc.querySelector('svg');
    if (svgElement) {
      svgElement.setAttribute('width', '100%');
      svgElement.setAttribute('height', '100%');
      svgElement.setAttribute('preserveAspectRatio', 'xMidYMid meet');
      svgElement.style.minWidth = '50vw';
      svgElement.style.minHeight = '50vh';
      svgElement.style.maxWidth = 'min(100vw, 100vh)';
      svgElement.style.maxHeight = 'min(100vw, 100vh)';
      svgElement.style.width = 'auto';
      svgElement.style.height = 'auto';
      svgElement.style.display = 'block';
      svgElement.style.margin = 'auto';
    }

    return new XMLSerializer().serializeToString(xmlDoc);
  };

  useEffect(() => {
    if (svgContent && onLoad) {
      onLoad();
    }
  }, [svgContent, onLoad]);

  return (
    <div dangerouslySetInnerHTML={{ __html: svgContent }} />
  );
};

export default SvgLoader;