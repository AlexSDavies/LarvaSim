function adjustPieText(h)

	for i = 2:2:length(h)
		
		th = h(i);
		
		pos = get(th,'Position');
		
		set(th,'Position',1.1*pos);
		
		
	end