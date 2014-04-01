function plotHeadCastAngles(headcastCounts)


	barProps = struct(				...							
		'Facecolor', [0.4 0.4 0.4], ...	
		'Linestyle','none'			...
		);

	[numPreTurnDivs numTurnAngleDivs] = size(headcastCounts);
	
	preTurnDivSize = 360 / numPreTurnDivs;

	turnAngleDivSize = 360 / numTurnAngleDivs;
	
	for i = 1:numPreTurnDivs
		startAngle(i) = -180 + (i-1) * preTurnDivSize;
		endAngle(i) = -180 + i * preTurnDivSize;
	end
	
	figure;

	for i = 1:numPreTurnDivs

		subplot(numPreTurnDivs,1,i);

		h = bar(-180+turnAngleDivSize/2:turnAngleDivSize:180-turnAngleDivSize/2,headcastCounts(i,:),1);
		
		set(h,barProps);
		
		xlim([-180,180]);
		hold on;
		
		set(gca,'XTick',[],'YTick',[]);
		
		% c = mean(turnAngles{i});

		ylim([0,0.2]);
		% plot([c,c],[0,40],'-r','linewidth',2);
		plot([0,0],[0,40],'-k','linewidth',2);
		
		ylabel([num2str(startAngle(i)) ' : ' num2str(endAngle(i))]);
		set(get(gca,'YLabel'),'Rotation',0,'Position',[-210,0.1,1]);
		
		
		if i == 1
			
			ylabel({'Pre head-cast','bearing','',[num2str(startAngle(i)) ' : ' num2str(endAngle(i))]});
			set(get(gca,'YLabel'),'Rotation',0,'Position',[-210,0.1,1]);
			
		end
		
		if i == numPreTurnDivs
			
			set(gca,'XTick',[-180,0,180]);
			xlabel('Head cast termination angles');
			
		end
		
		set(gcf,'Position',[400 200 710 770]);
		
	end