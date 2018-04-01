SESSION=snake


workspace:
	tmux new-session -s $(SESSION) -n main -d &&\
	tmux split-window &&\
	tmux select-layout main-vertical &&\
	tmux attach-session -t $(SESSION)

copy-assets:
	cp node_modules/codemirror/lib/codemirror.css resources/public/css/
	cp -r node_modules/codemirror/theme/ resources/public/css/

repl: src/js/lib.js
	lein figwheel

src/js/lib.js:
	node webpack.build.js
