package de.fhws.fiw.fds.sutton.server.api.states.get;

import de.fhws.fiw.fds.sutton.server.models.AbstractModel;

/**
 * <p>The AbstractGetRelationState extends the {@link AbstractGetState} with the required functionality to request
 * a single sub-resource related to a specific primary resource from the database.</p>
 *
 * <p>Each extending state class has to define a builder class, which must extend
 *  * {@link AbstractGetRelationState.AbstractGetRelationStateBuilder}</p>
 *
 * @see AbstractGetState
 * */
public abstract class AbstractGetRelationState<T extends AbstractModel> extends AbstractGetState<T>
{
	/**
	 * id {@link Long} of the primary resource
	 * */
	protected long primaryId;

	public AbstractGetRelationState( final AbstractGetRelationStateBuilder builder )
	{
		super( builder );
		this.primaryId = builder.parentId;
	}

	public static abstract class AbstractGetRelationStateBuilder extends AbstractGetStateBuilder
	{
		protected long parentId;

		public AbstractGetStateBuilder setParentId( final long parentId )
		{
			this.parentId = parentId;
			return this;
		}
	}
}
