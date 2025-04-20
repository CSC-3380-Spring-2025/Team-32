import { sveltekit } from '@sveltejs/kit/vite';
import { defineConfig } from 'vite';

export default defineConfig({
	plugins: [sveltekit()],
	server: {
		proxy: {
			'/game': 'http://localhost:8080'
			},
		fs: {
			allow: [
			// You can add paths that need to be served
			'node_modules/@sveltejs/kit',
			'src/main/svelte',
			'node_modules/@sveltejs/kit/src/runtime/client/entry.js',
			'C:/CodeStuff/Team-32-dev/Team-32-dev/node_modules/@sveltejs/kit/src/runtime/client/entry.js'
			// Add more directories if necessary
			],
		},
	},
  });