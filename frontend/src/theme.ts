import { createSystem, defaultConfig, defineConfig } from "@chakra-ui/react"

const customConfig = defineConfig({
  theme: {
    tokens: {
      fonts: {
        heading: { value: "'Saira', sans-serif" },
        body: { value: "'Saira', sans-serif" },
      },
      colors: {
        brand: {
      50: { value: '#e3f9ff'},
      100: { value: '#c8eaff'},
      200: { value: '#9bdcff'},
      300: { value: '#6ecfff'},
      400: { value: '#42c1ff'},
      500: { value: '#28a7e6'},
      600: { value: '#1c82b4'},
      700: { value: '#125d82'},
      800: { value: '#073951'},
      900: { value: '#001420'},
        },
      },
    },
  },
  globalCss: {
    body: {
      fontFamily: "body",
      bg: "brand.100",  
      color: "gray.800",
    },
    "*::placeholder": {
      opacity: 1,
      color: "fg.subtle",
    },
    "*::selection": {
      bg: "green.200",
    },
  },
})

export const system = createSystem(defaultConfig, customConfig)
