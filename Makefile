SESSION=snake

build:before cljs

copy-assets:
	cp node_modules/codemirror/lib/codemirror.css resources/public/css/
	cp -r node_modules/codemirror/theme/ resources/public/css/

repl:
	lein figwheel

cljsjs:
	node webpack.build.js

clean:
	lein clean

cljs:
	lein cljsbuild once min

before:clean copy-assets cljsjs

interactive:before repl

workspace:
	tmux new-session -s $(SESSION) -n main -d &&\
		tmux split-window &&\
		tmux select-layout main-vertical &&\
		tmux attach-session -t $(SESSION)
