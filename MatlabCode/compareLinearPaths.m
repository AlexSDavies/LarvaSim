function compareLinearPaths(inputPaths,models,segmentLengths,n)

% Only work with specified amount of paths data
inputPaths = inputPaths(models,:,1:n);

% For drawing
xlims = [441 1084];
ylims = [163 1167];

height = xlims(2) - xlims(1);
width = ylims(2) - ylims(1);

pathdraw = 0;

%% Get stats for each path

[noModels noConditions ~] = size(inputPaths);

for m = 1:noModels
	for c = 1:noConditions
		for s = 1:length(segmentLengths)
			
			fprintf('%i %i %i \n',m,c,s);
			
			for i = 1:n
				
				path = equalisePathSegments(inputPaths{m,c,i},segmentLengths(s));
				
				paths{m,c,s,i} = path;
				
				% Angles
				angles{m,c,s,i} = getPathAngles(path);
				
				
				dAngle{m,c,s,i} = normaliseAngle(diff(angles{m,c,i}));

				% Path length / angle stuff
				cosAngle{m,c,s,i} = mean(cos(angles{m,c,i}));
				sinAngle{m,c,s,i} = mean(sin(angles{m,c,i}));

				xSteps = path(2:end,1) - path(1:end-1,1);
				ySteps = path(2:end,2) - path(1:end-1,2);
				stepLengths = sqrt(xSteps.^2 + ySteps.^2);
				pathLength = sum(stepLengths);

				xDist{m,c,s,i} = sum(xSteps)/pathLength;
				yDist{m,c,s,i} = sum(ySteps)/pathLength;

				vectorAngle{m,c,s,i} = atan2(yDist{m,c,i},xDist{m,c,i});
				vectorLength{m,c,s,i} = sqrt(yDist{m,c,i}^2 + xDist{m,c,i}^2);

				% Path dist to right hand edge
				thresh = 0.9;
				t = find(path(:,1) > width*thresh,1);
				if(isempty(t))
					t = length(path);
				end
				distToOdour{m,c,s,i} = sum(stepLengths(1:t-1));

			end
						
		end
		
		% hist(dAngle{m,c,i},20);
		
	end
end


%% Get averages
for m = 1:noModels
	for c = 1:noConditions
		for s = 1:length(segmentLengths)
		
		averageVectorLength{m,c,s} = mean([vectorLength{m,c,s,:}]);
		averageVectorAngle{m,c,s} = atan2(sum([yDist{m,c,s,:}]),sum([xDist{m,c,s,:}]));
		averageVectorX{m,c,s} = mean([xDist{m,c,s,:}]);
		averageVectorY{m,c,s} = mean([yDist{m,c,s,:}]);
		
		end
	end
end



%% Plot

for s = 1:length(segmentLengths)

	vectorPathFig = figure;

	vectorLengthFig = figure;

	distToOdourFig = figure;

	reorientFig = figure;
	
	for m = 1:noModels
		for c = 1:noConditions

			% Subplots 
			
			subplotInd = (m-1)*noConditions+c;

			figure(vectorPathFig);
			vectorPathAxes = subplot(noModels,noConditions,subplotInd);
			hold on;

			figure(vectorLengthFig);
			vectorLengthAxes = subplot(noModels,noConditions,subplotInd);
			hold on;

			figure(distToOdourFig);
			distToOdourAxes = subplot(noModels,noConditions,subplotInd);
			hold on;
	
			figure(reorientFig);
			reorientAxes = subplot(noModels,noConditions,subplotInd);
			hold on;
			
			if pathdraw > 0
				pathFig = figure;
				set(gcf,'Name',['Model: ' num2str(m) '   Condition: ' num2str(c)])
			end

			
			% Drawing
			
			for i = 1:n

				plot(vectorPathAxes,[0 xDist{m,c,s,i}],[0,yDist{m,c,s,i}],'-k');

				if (i <= pathdraw)
					figure(pathFig);
					subplot(sqrt(pathdraw),sqrt(pathdraw),i);
					plot(paths{m,c,s,i}(:,1),paths{m,c,s,i}(:,2),'.-');
					xlim([0 width]); ylim([0 height]); set(gca,'XTick', [], 'YTick', []);
					axis equal;
				end

			end

			% Circular plot of path directions
			plot(vectorPathAxes,[0 averageVectorX{m,c,s}],[0,averageVectorY{m,c,s}],'-r','linewidth',4);
			axis(vectorPathAxes,[-1,1,-1,1],'equal');

			% Boxplot of path vector lengths (i.e. straightness)
			boxplot(vectorLengthAxes,[vectorLength{m,c,s,:}]);
			plot(vectorLengthAxes,xlim(vectorLengthAxes),[0 0],'--k');
			ylim(vectorLengthAxes,[-1,1]);

			% Boxplot of time to odour source
			boxplot(distToOdourAxes,[distToOdour{m,c,s,:}]);
			plot(distToOdourAxes,xlim(distToOdourAxes),[0 0],'--k');
			ylim(distToOdourAxes,[0,2500]);

			% Boxplot of reorientation angles
			reorientAngles = [];
			for i = 1:n
				reorientAngles = [reorientAngles [dAngle{m,c,s,i}]];
			end
			d = 2*pi/36; binCs = -pi+d/2:d:pi-d/2;
			bVals = hist(reorientAngles',binCs);
			bar(reorientAxes,binCs,bVals./sum(bVals),1);
			ylim(reorientAxes,[0 0.25]);

			
		end
	end

end
	
drawnow;


%% Stats
% 
% sig = 0.001;
% 
% statChecks = {'cosAngle','sinAngle','vectorLength','distToOdour'};
% 
% for s = 1:length(statChecks)
% 
% 	stat = eval([statChecks{s}]);
% 	
% 	disp(statChecks{s});
% 	
% 	for m = 1:noModels
% 
% 		disp(['Model ' num2str(m)]);
% 
% 		disp([mean(num2str([stat{m,1,:}])) ' ' num2str(mean([stat{m,2,:}])) ' ' num2str(mean([stat{m,3,:}]))]);
% 		
% 		sig1 = '';
% 		expVsSteep = ranksum([stat{m,1,:}],[stat{m,2,:}]);
% 		if expVsSteep < sig sig1 = '***'; end
% 		
% 		sig2 = '';
% 		steepVsShall = ranksum([stat{m,2,:}],[stat{m,3,:}]);
% 		if steepVsShall < sig sig2 = '***'; end
% 		
% 		fprintf('exp -> steep\tsteep -> shall\n%s%08f\t\t%s%08f\nn',sig1,expVsSteep,sig2,steepVsShall);
% 		% fprintf('%08f\n',expVsSteep);
% 		% disp('Steep / shall, p = ');
% 		% fprintf('%0.8f\n',steepVsShall);
% 
% 		
% 	end
% 	
% 	fprintf('\n\n');
% 		
% end




