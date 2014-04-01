function params = getHeadCastParams(filename)

numPreTurnDivs = 16;
numTurnAngleDivs = 36;

divs = -180+11.25:22.5:180-11.25;

counts = dlmread(['D:\Uni\LarvaSim\Input Data\bearing_and_turning\' filename '_ByBearing.csv'],',',0,0);

headCasts = getHeadCastBearings(filename,16,36);

for i = 1:numPreTurnDivs
	numCasts(i) = length(headCasts{i});
end


figure;
turnProb = numCasts./sum(numCasts);
absAngleTurnProb = (turnProb(9:16) + fliplr(turnProb(1:8)))

plot(divs(9:16),absAngleTurnProb,'.');

p = polyfit(divs(9:16),absAngleTurnProb,1)

hold on;
plot(divs(9:16),p(2) + p(1)*divs(9:16));

figure;
plot(turnProb)

% Model

pdf_normmixture = @(x,p,mu1,mu2,sigma1,sigma2) p*normpdf(x,mu1,sigma1) + (1-p)*normpdf(x,mu2,sigma2);
pStart = 0.5;
muStart = [-45 45];
sigmaStart = [20 20];
start = [pStart muStart sigmaStart];
lb = [0 -Inf -Inf 0 0];
ub = [1 Inf Inf Inf Inf];
options = statset('MaxIter',1200, 'MaxFunEvals',2400);
	

plotHeadCastAngles(counts);

for i = 1:numPreTurnDivs

	subplot(16,1,i); hold on;
	
	headCasts{i} = headCasts{i}(abs(headCasts{i}) < 90);
	
	params(i,:) = mle(headCasts{i},'pdf',pdf_normmixture,'start',start,'upper',ub,'lower',lb,'options',options);
	
	ix = -180:1:180;
	iy = pdf_normmixture(ix,params(i,1),params(i,2),params(i,3),params(i,4),params(i,5));
	iy = iy.*10;
	
	hold on;
	plot(ix,iy,'-r');
	plot([params(i,2) params(i,2)],ylim,'Linewidth',params(i,1)*2);
	plot([params(i,3) params(i,3)],ylim,'Linewidth',(1-params(i,1))*2);
	
	
end


params(:,6) = turnProb';






