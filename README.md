prismic.io code generator - WIP

## Development

- Install [Leiningen](http://leiningen.org/), the clojure build tool.

- From the project directory, run `lein cljsbuild auto dev`.

It will compile the sources from the `src/` directory to `public/js/compiled.js`.

## Deployment

Compile with Google Closure compiler optimizations: `lein cljsbuild once prod`.
This will replace `public/js/compiled.js` with smaller and faster JS code.

## Relevant links

- Include javascript libraries http://lukevanderhart.com/2011/09/30/using-javascript-and-clojurescript.html
