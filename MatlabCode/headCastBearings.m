
files = {
'Naive 1_5'
'Naive 1_50'
'Naive 1_500'
'Naive 1_5000'
};

clear counts;

for f = 1:length(files)

	data = dlmread(['../Input Data/bearing_and_turning/' files{f} '.csv'],',',2,0);

	%%

	numPreTurnDivs = 16;

	numTurnAngleDivs = 36;
	
	preTurnDivSize = 360 / numPreTurnDivs;

	turnAngleDivSize = 360 / numTurnAngleDivs;
	
	% Split data according to bearing at turn initiation
	for i = 1:numPreTurnDivs

		startAngle(i) = -180 + (i-1) * preTurnDivSize;
		endAngle(i) = -180 + i * preTurnDivSize;
		
		a1 = startAngle(i);
		a2 = endAngle(i);
		
		turnAngles{i} = data(find(data(:,1) > a1 & data(:,1) < a2),2);
		
		numTurns(i) = length(turnAngles{i});
		
	end
	
	numAbsTurns = fliplr(numTurns(1:numPreTurnDivs/2)) + numTurns(numPreTurnDivs/2+1:numPreTurnDivs);
	numAbsTurnsO(f,:) = numAbsTurns ./ sum(numAbsTurns);
% 	figure;
% 	cumTurns(1) = numAbsTurns(1);
% 	for i = 2:length(numAbsTurns)
% 		cumTurns(i) = numAbsTurns(i) + cumTurns(i-1);
% 	end
% 	length([0:preTurnDivSize:180-preTurnDivSize])
% 	length(numAbsTurns)
% 	plot([0:preTurnDivSize:180-preTurnDivSize],numAbsTurns);
	
	%%
	figure;

	for i = 1:numPreTurnDivs

		subplot(numPreTurnDivs,1,i);

		counts(i,:) = hist(turnAngles{i},-180+turnAngleDivSize/2:turnAngleDivSize:180-turnAngleDivSize/2);
		counts(i,:) = counts(i,:)./sum(counts(i,:));
		
		bar(-180+turnAngleDivSize/2:turnAngleDivSize:180-turnAngleDivSize/2,counts(i,:),1);
		
		xlim([-180,180]);
		hold on;
		
		set(gca,'XTick',[],'YTick',[]);
		
		c = mean(turnAngles{i});

		ylim([0,0.2]);
		plot([c,c],[0,40],'-r','linewidth',2);
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
	
	saveeps('D:\Dropbox\Uni\PhD\ReviewFigures\FullStatLarva\',['HeadCastAngles_' files{f}]);

	propAbsTurns = numAbsTurns./sum(numAbsTurns);

	csvwrite(['../Input Data/bearing_and_turning/' files{f} '_ByBearing.csv'],counts);
	csvwrite(['../Input Data/bearing_and_turning/' files{f} '_TurnBearings.csv'],propAbsTurns);

end

