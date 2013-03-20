function matchAxes

	subfigsInds = get(gcf,'children');
	subfigs = get(subfigsInds);
	
	yVal = max(abs([subfigs.YLim]));
	xVal = max(abs([subfigs.XLim]));
	
	for i = 1:length(subfigsInds)
		set(subfigsInds(i),'YLim',[-yVal yVal]);
		set(subfigsInds(i),'XLim',[-xVal xVal]);
	end