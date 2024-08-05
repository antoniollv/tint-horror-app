import React, { useEffect, useState } from 'react';

const SvgLoader = () => {
  const [svgContent, setSvgContent] = useState('');

  useEffect(() => {
    const fetchSvg = async () => {
      try {
        const response = await fetch('/tinwebp.svg');
        const text = await response.text();
        const cleanedSvg = cleanSvg(text);
        setSvgContent(cleanedSvg);
      } catch (error) {
        console.error('Error loading SVG:', error);
      }
    };

    fetchSvg();
  }, []);

  const cleanSvg = (content) => {
    const parser = new DOMParser();
    const xmlDoc = parser.parseFromString(content, 'image/svg+xml');
    
    const allowedTags = ['svg', 'path', 'circle', 'rect', 'line', 'polyline', 'polygon', 'text', 'g'];
    const elements = xmlDoc.getElementsByTagName('*');

    for (let i = elements.length - 1; i >= 0; i--) {
      const node = elements[i];
      if (!allowedTags.includes(node.tagName)) {
        node.parentNode.removeChild(node);
      }
    }

    return new XMLSerializer().serializeToString(xmlDoc);
  };

  return (
    <div>
      <div dangerouslySetInnerHTML={{ __html: svgContent }} />
    </div>
  );
};

export default SvgLoader;
