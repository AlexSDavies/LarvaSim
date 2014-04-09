

%%

clear;

n = 100;

bearings = [];
reorientations = [];

for i = 1:n
	
	disp(i)

	stats = getSimStats(['WeathervaneTest' num2str(i)],0.1);

	allBearing = stats.data.bearing;
	allAngle = stats.data.angle;

	allReorientation = (allAngle(2:end) - allAngle(1:end-1))./0.1;

	runIndeces = find(abs(allReorientation) < deg2rad(15));

	runBearingsLarva = allBearing(runIndeces);
	runReorientationsLarva = allReorientation(runIndeces);

	runBearingsLarva(runBearingsLarva > pi) = -2*pi + runBearingsLarva(runBearingsLarva > pi);

	bearings = [bearings; runBearingsLarva];
	reorientations = [reorientations; runReorientationsLarva];
	
end

%%

figure; hold on;
plot(stats.data.headPos(:,1),stats.data.headPos(:,2),'-r');
plot(stats.data.tailPos(:,1),stats.data.tailPos(:,2),'-k','linewidth',2);

%% 

colormap('autumn');
c = colormap;

hold on;
for i = 1:length(allReorientation)
	
	% if allReorientation(i) > deg2rad(15)
	if allBearing(i) > pi/2 && allBearing(i) < pi+pi/2
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

a = [-180:10:170];

for i = 1:length(a)
	
	ang = a(i);
	
	ind = find(bearings > deg2rad(ang) & bearings < deg2rad(ang+10));
	
	reorientationAtBearing(i) = rad2deg(mean(reorientations(ind)));
	
end

plot(a+5,reorientationAtBearing); xlim([-180 180]); ylim([-3 3]);
hold on;
plot([-180 180],[0 0],'-k')
plot([0 0],ylim,'--k')

xlabel('Bearing');
ylabel('Reorientation (deg/s)');

%%

clear b xs left* right*;


b = deg2rad(-10:1:10);
xs = rad2deg((b(1:end-1)+b(2:end))/2);

% All

for i = 1:length(b)-1
	
	leftReorient(i) = length(find(reorientations > b(i) & reorientations < b(i+1) & bearings > 0));
	rightReorient(i) = length(find(reorientations > b(i) & reorientations < b(i+1)  & bearings < 0));
	
end

leftReorient = leftReorient./length(reorientations);
rightReorient = rightReorient./length(reorientations);

subplot(3,1,1); hold on;
plot(xs,leftReorient,'-m','linewidth',2);
plot(xs,rightReorient,'-g','linewidth',2);

ylim([0, 0.04]);
plot([0 0],ylim,'-k');

title('Whole path');
xlabel('Reorientation (deg/s)');


disp('All');
rad2deg(mean(reorientations(reorientations > 0))) - rad2deg(mean(reorientations(reorientations < 0)))


% Towards

towardsIndices = find(bearings > -pi/2 & bearings < pi/2);
bearingTowards = bearings(towardsIndices);
reorientationTowards = reorientations(towardsIndices);


for i = 1:length(b)-1
	
	leftReorientTowards(i) = length(find(reorientationTowards > b(i) & reorientationTowards < b(i+1) & bearingTowards > 0));
	rightReorientTowards(i) = length(find(reorientationTowards > b(i) & reorientationTowards < b(i+1) & bearingTowards < 0));
	
end

leftReorientTowards = leftReorientTowards./length(reorientationTowards);
rightReorientTowards = rightReorientTowards./length(reorientationTowards);

subplot(3,1,2); hold on;
plot(xs,leftReorientTowards,'-m','linewidth',2);
plot(xs,rightReorientTowards,'-g','linewidth',2);

ylim([0, 0.04]);
plot([0 0],ylim,'-k');

title('Towards odour only');
xlabel('Reorientation (deg/s)');


disp('Towards');
rad2deg(mean(reorientationTowards(reorientationTowards < 0))) - rad2deg(mean(reorientationTowards(reorientationTowards > 0)))



% Away

awayIndices = find(bearings < -pi/2 | bearings > pi/2);
bearingAway = bearings(awayIndices);
reorientationAway = reorientations(awayIndices);

for i = 1:length(b)-1
	
	leftReorientAway(i) = length(find(reorientationAway > b(i) & reorientationAway < b(i+1) & bearingAway > 0));
	rightReorientAway(i) = length(find(reorientationAway > b(i) & reorientationAway < b(i+1)  & bearingAway < 0));
	
end

leftReorientAway = leftReorientAway./length(reorientationAway);
rightReorientAway = rightReorientAway./length(reorientationAway);

subplot(3,1,3); hold on;
plot(xs,leftReorientAway,'-m','linewidth',2);
plot(xs,rightReorientAway,'-g','linewidth',2);

ylim([0, 0.04]);
plot([0 0],ylim,'-k');

title('Away from odour only');
xlabel('Reorientation (deg/s)');


disp('Away');
rad2deg(mean(reorientationAway(reorientationAway > 0))) - rad2deg(mean(reorientationAway(reorientationAway < 0)))



