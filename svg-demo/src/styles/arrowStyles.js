// src/styles/arrowStyles.js
import { css } from 'styled-components';

export const arrowPositionStyles = {
  'arriba-derecha': css`
    &::before {
      left: 60%;
      top: calc(var(--arrow) / -2);
      transform: rotateZ(180deg) scaleX(-1);
      box-shadow: 
        inset calc(var(--arrow)/2) calc(var(--arrow)/3) 0 0 white;      
    }
  `,
  'arriba-izquierda': css`
    &::before {
      left: 10px;
      top: calc(var(--arrow) / -2);
      transform: rotateZ(180deg);
      box-shadow: 
        inset calc(var(--arrow)/2) calc(var(--arrow)/3) 0 0 white;
    }
  `,
  'derecha-arriba': css`
    &::before {
      top: 10px;
      right: calc(var(--arrow) * -.15);
      transform: skewY(30deg) rotateZ(30deg);
      box-shadow: 
        inset calc(var(--arrow)/2) calc(var(--arrow)/3) 0 0 white;
    }
  `,
  'derecha-abajo': css`
    &::before {
      bottom: calc(var(--arrow) / 2 * -1);
      right: calc(var(--arrow) * -1);
      transform: skewX(30deg) rotateZ(30deg);
    }
  `,
  'abajo-derecha': css`
    &::before {
      left: 65%;
      bottom: calc(var(--arrow) / -1.4);
      transform: skewY(-30deg) rotateZ(0deg);
      box-shadow: 
        inset calc(var(--arrow)/3) calc(var(--arrow)/3) 0 0 white;
    }
  `,
  'abajo-izquierda': css`
    &::before {
      right: 75%;
      bottom: calc(var(--arrow) / -1.5);
      transform: rotateZ(0deg) scaleX(-2);
      box-shadow: 
        inset calc(var(--arrow)/3) calc(var(--arrow)/3) 0 0 white;
    }
  `,
  'izquierda-arriba': css`
    &::before {
      left: -20px;
      bottom: calc(var(--arrow) / 1);
      transform: rotateZ(90deg) scaleX(-1);
      box-shadow: 
        inset calc(var(--arrow)/3) calc(var(--arrow)/3) 0 0 white;
    }
  `,
  'izquierda-abajo': css`
    &::before {
      bottom: calc(var(--arrow) / 2 * -1);
      left: calc(var(--arrow) * -1);
      transform: skewX(-30deg) rotateZ(-30deg);
    }
  `,
};
