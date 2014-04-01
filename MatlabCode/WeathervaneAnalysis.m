

%%

clear;

stats = getSimStats('WeathervaneTest1',0.1);


bearing = stats.data.bearing;
angle = stats.data.angle;

reorientation = (angle(2:end) - angle(1:end-1))./0.1;

runIndeces = find(abs(reorientation) < deg2rad(15));

%%

figure; hold on;
plot(stats.data.headPos(:,1),stats.data.headPos(:,2),'-r');
plot(stats.data.tailPos(:,1),stats.data.tailPos(:,2),'-k','linewidth',2);

%%

figure;
plot(reorientation);

%% 

colormap('autumn');
c = colormap;

figure; hold on;
for i = 1:length(reorientation)
	
	if reorientation(i) > deg2rad(15)
		col = [1 0 0];
	else
		col = [0 0 0];
	end
	
	%val = abs(reorientation(i))/deg2rad(12);
	%val = min(val,1);
	%col = c(round(val*63)+1,:);
	
	plot(stats.data.tailPos(i:i+1,1),stats.data.tailPos(i:i+1,2),'color',col,'linewidth',2);
	
end

%%

	
runBearings = bearing(runIndeces);
runReorientations = reorientation(runIndeces);

a = [0:10:350];

for i = 1:length(a)
	
	ang = a(i);
	
	ind = find(runBearings > deg2rad(ang) & runBearings < deg2rad(ang+10));
	
	reorientationAtBearing(i) = rad2deg(mean(runReorientations(ind)));
	
end

plot(a+5,reorientationAtBearing); xlim([0 360]); ylim([-3 3]);
hold on;
plot([0 360],[0 0],'-k')
plot([180 180],ylim,'--k')

xlabel('Bearing');
ylabel('Reorientation (deg/s)');

