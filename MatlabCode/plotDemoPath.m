stats = getSimStats('DemoPath',0.1);

%%

div = 0.1;

x = -50:div:50;
y = -50:div:50;

clear odour

for i = 1:length(x)
	for j = 1:length(y)
		
		xScaledDist = x(i)/15;
		yScaledDist = y(j)/15;
		
		val = exp(-(xScaledDist^2)-(yScaledDist^2));
		
		odour(i,j) = val;
	end
end


imagesc([-50,50],[-50,50],odour);
alpha(0.6);

hold on; axis ij;

plot(stats.data.headPos(:,1),stats.data.headPos(:,2),'-m','linewidth',2);
plot(stats.data.midPos(:,1),stats.data.midPos(:,2),'k','linewidth',2);

% colormap(cm);

xlim([-30,30]); ylim([-30,30]); set(gcf,'position',[100,300,500,500]);

set(gca,'XTickLabel',{'0','1','2','3','4','5','6'});
set(gca,'YTickLabel',{'6','5','4','3','2','1',''});

xlabel('Distance (cm)');

%%

subplot(2,1,1); hold on;
plot(stats.data.time,stats.data.odourVal,'-m','linewidth',1.5);
xlabel('Time (s)'); ylabel('Concentration'); ylim([0,1]);

subplot(2,1,2); hold on; plot([0,60],[0,0],'-k');
plot(stats.data.time,stats.data.perception,'-m','linewidth',1.5);
xlabel('Time (s)'); ylabel('Perception'); ylim([-0.05,0.05]);

set(gcf,'Position',[226 349 1406 552]);

saveeps('D:\Dropbox\Uni\PhD\ReviewFigures\Misc\','Perception');

%%

dataFile = 'data_DemoPath';
rawData = dlmread(['../Data/' dataFile], ' ',2,0);
runTermRate = rawData(:,14) * 0.1;
castTermRate = rawData(:,15) * 0.1;

subplot(3,1,1); hold on; plot([0,60],[0,0],'-k');
plot(stats.data.time,stats.data.perception,'-m','linewidth',1.5);
xlabel('Time (s)'); ylabel('Perception'); ylim([-0.05,0.05]);

subplot(3,1,2); hold on;
plot(stats.data.time,runTermRate,'-m','linewidth',1.5);
plot([0,60],[0,0],'-k');
xlabel('Time (s)'); ylabel('Run termination rate (Hz)');
ylim([-0.1,0.1]);

subplot(3,1,3); hold on;
plot(stats.data.time,castTermRate,'-m','linewidth',1.5);
plot([0,60],[0,0],'-k');
xlabel('Time (s)'); ylabel('Cast termination rate (Hz)');
ylim([-1,1]);

set(gcf,'Position',[226 349 1406 552]);

saveeps('D:\Dropbox\Uni\PhD\ReviewFigures\Misc\','TransitionRates');

