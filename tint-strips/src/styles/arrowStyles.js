// src/styles/arrowStyles.js
import { css } from 'styled-components';

export const arrowPositionStyles = {
  'none': css`
    background-color: white;
    border: none;
    padding: 0;
    filter: none;
    max-width: none;
    white-space: nowrap;

    &::before {
      display: none;
      content: none;
    }
  `,
  'top-right': css`
    &::before {
      left: 60%;
      top: calc(var(--arrow) / -2);
      transform: rotateZ(180deg) scaleX(-1);
      box-shadow: 
        inset calc(var(--arrow)/2) calc(var(--arrow)/3) 0 0 white;      
    }
  `,
  'top-left': css`
    &::before {
      left: 10px;
      top: calc(var(--arrow) / -2);
      transform: rotateZ(180deg);
      box-shadow: 
        inset calc(var(--arrow)/2) calc(var(--arrow)/3) 0 0 white;
    }
  `,
  'right-top': css`
    &::before {
      top: 25px;
      right: calc(var(--arrow) * -0.60);
      transform: skewY(-30deg) rotateZ(270deg);
      box-shadow: 
        inset calc(var(--arrow)/2) calc(var(--arrow)/3) 0 0 white;
    }
  `,
  'right-bottom': css`
    &::before {
      bottom: 20%;
      right: calc(var(--arrow) * -0.65);
      transform: skewY(-30deg) rotateZ(270deg) scaleX(-1);;
      box-shadow: 
        inset calc(var(--arrow)/2) calc(var(--arrow)/3) 0 0 white;
    }
  `,
  'bottom-right': css`
    &::before {
      left: 65%;
      bottom: calc(var(--arrow) / -1.4);
      transform: skewY(-30deg) rotateZ(0deg);
      box-shadow: 
        inset calc(var(--arrow)/3) calc(var(--arrow)/3) 0 0 white;
    }
  `,
  'bottom-left': css`
    &::before {
      right: 65%;
      bottom: calc(var(--arrow) / -1.5);
      transform: rotateZ(0deg) scaleX(-2);
      box-shadow: 
        inset calc(var(--arrow)/3) calc(var(--arrow)/3) 0 0 white;
    }
  `,
  'left-top': css`
    &::before {
      left: -20px;
      bottom: calc(var(--arrow) / 1);
      transform: rotateZ(90deg) scaleX(-1);
      box-shadow: 
        inset calc(var(--arrow)/3) calc(var(--arrow)/3) 0 0 white;
    }
  `,
  'left-bottom': css`
    &::before {
      left: -6%;
      bottom: 20%;
      transform: rotateZ(90deg) scaleX(-1);
      box-shadow: 
        inset calc(var(--arrow)/3) calc(var(--arrow)/3) 0 0 white;
    }
  `
};
