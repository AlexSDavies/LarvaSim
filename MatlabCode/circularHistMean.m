% Calculates the circular mean given the angles for
% a number of bins and the counts which fall into them
function mean = circularHistMean(angles,counts)
   
    n = length(angles);

    ySum = sum(counts.*sind(angles));
    xSum = sum(counts.*cosd(angles));

    mean = rad2deg(atan2(ySum/n,xSum/n));