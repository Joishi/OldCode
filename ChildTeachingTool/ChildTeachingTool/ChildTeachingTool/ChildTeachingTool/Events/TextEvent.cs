using System;

namespace ChildTeachingTool.StatesAndProperties
{
    public class TextEvent : EventArgs
    {
        public TextState eventType { get; set; }

        public TextEvent(TextState eventType)
        {
            this.eventType = eventType;
        }
    }
}
