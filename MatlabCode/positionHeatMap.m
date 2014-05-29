function heatmap = positionHeatMap(positions,xLimits,yLimits,divSize)

% 	positions = [];
% 
% 	for i = 1:num
% 		stats = getSimStats([name int2str(i)],0.1);	
% 		positions = [positions; stats.data.midPos];
% 	end



	xRange = xLimits(2) - xLimits(1);
	yRange = yLimits(2) - yLimits(1);
	
	divsX = xLimits(1):divSize:xLimits(2);
	divsY = yLimits(1):divSize:yLimits(2);
	
	divNumX = length(divsX);
	divNumY = length(divsY);
	
	edges{1} = divsX;
	edges{2} = divsY;
	

	
	heatmap = hist3(positions,'edges',edges);


	heatmap = heatmap./length(positions);
	
	
	m = flipud(colormap('gray'));
	maxVal = max(max(heatmap));
	numCols = length(m);
	
	
	% imagesc(heatmap);
	
	% Draw this manually as need to keep scale correct for 
	% overlaying paths
	for x = 1:divNumX
		for y = 1:divNumY
						
			col = m(ceil(heatmap(x,y)*(numCols-1)/maxVal+1),:);
			
			sX = divSize*1.1;
			sY = divSize*1.1;
			if x == length(divsX); sX = divSize; end
			if y == length(divsY); sY = divSize; end
			
			rectangle('Position',[divsX(x) divsY(y) sX sY],'FaceColor',col,'LineStyle','none');
			
		end
	end
	
	
	% set(gca,'CLim',[0 maxVal])
	% cb = colorbar();
	
	axis off;
	% axis equal;