
function rosePlus(data,color,alpha)

    f = rose(data,32);
    
    set(f,'Color','k','LineWidth',1);
 	axis tight;

    set(gca,'View',[90,90]);
    h = patch(get(f,'XData'),get(f,'YData'),color,'FaceAlpha',alpha);
 	
	set(gca,'XTick',[],'YTick',[]);
	axes = get(gca);
	maxLim = max([abs(axes.XLim) abs(axes.YLim)]);
	set(gca,'XLim',[-maxLim maxLim]);
	set(gca,'YLim',[-maxLim maxLim]);
	
	set(h,'EdgeColor','k','LineWidth',1.2);
	hold on;
	l = line([0 0],[-maxLim,maxLim]);
	set(l,'Color',[0 0 0]);
	l = line([-maxLim,maxLim],[0,0]);
	set(l,'Color',[0 0 0]);
	axis off;
	
