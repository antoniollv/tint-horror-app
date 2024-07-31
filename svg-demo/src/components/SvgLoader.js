import React, { useEffect, useState } from 'react';

const SvgLoader = ({ src }) => {
  const [svgContent, setSvgContent] = useState('');

  useEffect(() => {
    const fetchSvg = async () => {
      try {
        const response = await fetch(src);
        const text = await response.text();
        const cleanedSvg = cleanSvg(text);
        setSvgContent(cleanedSvg);
      } catch (error) {
        console.error('Error loading SVG:', error);
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
      svgElement.setAttribute('width', '50%');
      svgElement.setAttribute('height', 'auto');
    }

    return new XMLSerializer().serializeToString(xmlDoc);
  };

  return (    
    <div dangerouslySetInnerHTML={{ __html: svgContent }} />
  );
};

export default SvgLoader;
