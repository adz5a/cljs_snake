SESSION=snake


workspace:
	tmux new-session -s $(SESSION) -n main -d &&\
	tmux split-window &&\
	tmux select-layout main-vertical &&\
	tmux attach-session -t $(SESSION)
