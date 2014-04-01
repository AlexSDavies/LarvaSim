function heatmap = positionHeatMap(positions,xLimits,yLimits,divSize)

% 	positions = [];
% 
% 	for i = 1:num
% 		stats = getSimStats([name int2str(i)],0.1);	
% 		positions = [positions; stats.data.midPos];
% 	end

	f = gcf;

	xRange = xLimits(2) - xLimits(1);
	yRange = yLimits(2) - yLimits(1);
	
	divsX = xLimits(1):divSize:xLimits(2);
	divsY = yLimits(1):divSize:yLimits(2);
	
	divNumX = length(divsX);
	divNumY = length(divsY);
	
	heatmap = zeros(divNumX,divNumY);
	
	for xI = 1:divNumX
		for yI = 1:divNumY
			
			% h = waitbar(((xI*divNumY+yI))/(divNumX*divNumY));
			
			x = divsX(xI);
			y = divsY(yI);
			
			heatmap(xI,yI) = sum(positions(:,1) >= x & positions(:,1) < x+divSize & positions(:,2) >= y & positions(:,2) < y+divSize);
			
		end
	end
	
	% close(h);

	% Convert positions to div points
% 	positions = floor((positions+maxPos)./divSize);
% 	positions(positions == divNum) = divNum-1;
% 
% 	n = length(positions);
% 	
% 	for i = 1:length(positions)
% 		waitbar(i/n);
% 		x = positions(i,1)+1;
% 		y = positions(i,2)+1;
% 		heatmap(x,y) = heatmap(x,y)+1;
% 	end

	heatmap = heatmap./length(positions);
	
	
	m = colormap('hot');
	maxVal = max(max(heatmap));
	numCols = length(m);
	% imagesc(heatmap);
	
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
	
	divNumX*divNumY
	
	
	% set(gca,'CLim',[0 maxVal])
	% cb = colorbar();
	
	
	axis off;
	axis equal;